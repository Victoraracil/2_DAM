from odoo import models, fields, api
from odoo.exceptions import ValidationError


class HelpdeskIncidencia(models.Model):
    _name = 'helpdesk.karma.incidencia'
    _description = 'Incidencia HelpDesk con Karma'
    _order = 'create_date desc'

    name = fields.Char(string="Título", required=True)
    description = fields.Text(string="Descripción")

    # Usuario que crea la incidencia
    usuario_id = fields.Many2one(
        'res.users',
        string="Usuario",
        required=True
    )

    # Técnico asignado automáticamente
    tecnico_id = fields.Many2one(
        'res.users',
        string="Técnico",
        domain=[('helpdesk_role', '=', 'tecnico')]
    )

    # Gravedad
    gravedad = fields.Selection([
        ('baja', 'Baja'),
        ('media', 'Media'),
        ('alta', 'Alta'),
        ('critica', 'Crítica')
    ], string="Gravedad", default='media')

    # Prioridad automática según karma del usuario
    prioridad = fields.Selection([
        ('baja', 'Baja'),
        ('media', 'Media'),
        ('alta', 'Alta')
    ], string="Prioridad",
       compute="_compute_prioridad",
       store=True)

    # Estados
    estado = fields.Selection([
        ('nuevo', 'Nuevo'),
        ('asignado', 'Asignado'),
        ('en_proceso', 'En proceso'),
        ('resuelto', 'Resuelto'),
        ('cerrado', 'Cerrado'),
        ('reabierto', 'Reabierto')
    ], string="Estado", default='nuevo')

    fecha_resolucion = fields.Datetime(string="Fecha Resolución")

    # CREATE y Asignación automática
    @api.model_create_multi
    def create(self, vals_list):

        for vals in vals_list:

            usuario_id = vals.get('usuario_id')
            if usuario_id:
                usuario = self.env['res.users'].browse(usuario_id)

                # Restricción karma negativo
                if usuario.karma_total < 0 and vals.get('gravedad') == 'critica':
                    raise ValidationError(
                        "Los usuarios con karma negativo no pueden crear incidencias críticas."
                    )

            # Asignación automática técnico
            tecnico = self.env['res.users'].search(
                [('helpdesk_role', '=', 'tecnico')],
                order='karma_total desc',
                limit=1
            )

            if tecnico:
                vals['tecnico_id'] = tecnico.id

        return super().create(vals_list)



    # PRIORIDAD AUTOMÁTICA
    @api.depends('usuario_id.karma_total')
    def _compute_prioridad(self):
        for record in self:
            if record.usuario_id.karma_total < 0:
                record.prioridad = 'alta'
            elif 0 <= record.usuario_id.karma_total < 50:
                record.prioridad = 'media'
            else:
                record.prioridad = 'baja'

    # MÉTODO AUXILIAR KARMA
    def _actualizar_karma(self, user, puntos, accion):
        self.env['helpdesk.karma.historial'].create({
            'user_id': user.id,
            'incidencia_id': self.id,
            'accion': accion,
            'puntos': puntos
        })

        user.karma_total += puntos

    # CAMBIOS DE ESTADO
    def action_asignar(self):
        self.estado = 'asignado'

    def action_en_proceso(self):
        self.estado = 'en_proceso'

    def action_resolver(self):
        self.estado = 'resuelto'
        self.fecha_resolucion = fields.Datetime.now()

        if self.tecnico_id:
            self._actualizar_karma(
                self.tecnico_id,
                10,
                "Resolución de incidencia"
            )

    def action_cerrar(self):
        self.estado = 'cerrado'

        if self.usuario_id:
            self._actualizar_karma(
                self.usuario_id,
                5,
                "Incidencia cerrada correctamente"
            )

    def action_reabrir(self):
        self.estado = 'reabierto'

        if self.tecnico_id:
            self._actualizar_karma(
                self.tecnico_id,
                -5,
                "Incidencia reabierta"
            )
