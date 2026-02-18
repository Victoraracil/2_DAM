from odoo import models, fields


class HelpdeskKarmaHistorial(models.Model):
    _name = 'helpdesk.karma.historial'
    _description = 'Historial de movimientos de Karma'
    _order = 'fecha desc'

    user_id = fields.Many2one(
        'res.users',
        string="Usuario",
        required=True
    )

    incidencia_id = fields.Many2one(
        'helpdesk.karma.incidencia',
        string="Incidencia"
    )

    accion = fields.Char(string="Acción")

    puntos = fields.Integer(string="Puntos")

    fecha = fields.Datetime(
        string="Fecha",
        default=fields.Datetime.now
    )
