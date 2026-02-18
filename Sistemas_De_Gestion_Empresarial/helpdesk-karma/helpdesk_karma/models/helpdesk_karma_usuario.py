from odoo import models, fields, api

class ResUsers(models.Model):
    _inherit = 'res.users'

    # Rol dentro del HelpDesk
    helpdesk_role = fields.Selection([
        ('usuario', 'Usuario'),
        ('tecnico', 'Técnico')
    ], string="Rol HelpDesk")

    # Karma acumulado
    karma_total = fields.Integer(
        string="Karma Total",
        default=0
    )

    # Nivel derivado del karma
    nivel_karma = fields.Selection([
        ('novato', 'Novato'),
        ('intermedio', 'Intermedio'),
        ('experto', 'Experto'),
        ('maestro', 'Maestro')
    ], string="Nivel",
       compute="_compute_nivel_karma",
       store=True
    )

    @api.depends('karma_total')
    def _compute_nivel_karma(self):
        for user in self:
            if user.karma_total < 0:
                user.nivel_karma = 'novato'
            elif 0 <= user.karma_total < 50:
                user.nivel_karma = 'intermedio'
            elif 50 <= user.karma_total < 150:
                user.nivel_karma = 'experto'
            else:
                user.nivel_karma = 'maestro'
