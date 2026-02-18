from odoo import models, fields, api

class HelpdeskKarmaTecnico(models.Model):
    _name = 'helpdesk.karma.tecnico'
    _description = 'Técnico del sistema Helpdesk Karma'

    name = fields.Char(string="Nombre", required=True)
    especialidad = fields.Selection([
        ('redes', 'Redes'),
        ('software', 'Software'),
        ('hardware', 'Hardware'),
        ('general', 'General'),
    ], string="Especialidad", default='general')

    karma_tecnico = fields.Integer(string="Karma", default=0)
