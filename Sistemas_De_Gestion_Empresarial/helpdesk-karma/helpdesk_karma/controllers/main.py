from odoo import http
from odoo.http import request
import json


class HelpdeskKarmaAPI(http.Controller):

    #Incidencias asociadas a un usuario
    @http.route('/api/usuarios/<int:usuario_id>/incidencias',
                type='http', auth='public', methods=['GET'])
    def incidencias_usuario(self, usuario_id, **kwargs):

        incidencias = request.env['helpdesk.karma.incidencia'].sudo().search([
            ('usuario_id', '=', usuario_id)
        ])

        resultado = []

        for inc in incidencias:
            resultado.append({
                'id': inc.id,
                'titulo': inc.name,
                'estado': inc.estado,
                'gravedad': inc.gravedad,
                'prioridad': inc.prioridad,
                'karma_usuario_actual': inc.usuario_id.karma_total
            })

        response = {
            'usuario_id': usuario_id,
            'total_incidencias': len(resultado),
            'incidencias': resultado
        }

        return request.make_response(
            json.dumps(response),
            headers=[('Content-Type', 'application/json')]
        )

    #Estado y rendimiento de un técnico
    @http.route('/api/tecnicos/<int:tecnico_id>/estado',
                type='http', auth='public', methods=['GET'])
    def estado_tecnico(self, tecnico_id, **kwargs):

        tecnico = request.env['res.users'].sudo().browse(tecnico_id)

        if not tecnico.exists() or tecnico.helpdesk_role != 'tecnico':
            response = {'error': 'Técnico no encontrado'}
            return request.make_response(
                json.dumps(response),
                headers=[('Content-Type', 'application/json')]
            )

        incidencias = request.env['helpdesk.karma.incidencia'].sudo().search([
            ('tecnico_id', '=', tecnico_id)
        ])

        total = len(incidencias)
        resueltas = len(incidencias.filtered(
            lambda i: i.estado in ['resuelto', 'cerrado']
        ))
        reabiertas = len(incidencias.filtered(
            lambda i: i.estado == 'reabierto'
        ))

        rendimiento = 0
        if total > 0:
            rendimiento = round((resueltas / total) * 100, 2)

        response = {
            'tecnico_id': tecnico.id,
            'nombre': tecnico.name,
            'karma_total': tecnico.karma_total,
            'nivel': tecnico.nivel_karma,
            'total_incidencias': total,
            'resueltas': resueltas,
            'reabiertas': reabiertas,
            'rendimiento_porcentaje': rendimiento
        }

        return request.make_response(
            json.dumps(response),
            headers=[('Content-Type', 'application/json')]
        )


    #Resumen global del sistema
    @http.route('/api/sistema/resumen',
                type='http', auth='public', methods=['GET'])
    def resumen_sistema(self, **kwargs):

        Incidencia = request.env['helpdesk.karma.incidencia'].sudo()
        Usuarios = request.env['res.users'].sudo()

        incidencias = Incidencia.search([])

        total = len(incidencias)
        abiertas = len(incidencias.filtered(
            lambda i: i.estado in ['nuevo', 'asignado', 'en_proceso']
        ))
        cerradas = len(incidencias.filtered(
            lambda i: i.estado == 'cerrado'
        ))
        reabiertas = len(incidencias.filtered(
            lambda i: i.estado == 'reabierto'
        ))

        tecnicos = Usuarios.search([('helpdesk_role', '=', 'tecnico')])

        media_karma = 0
        if tecnicos:
            media_karma = round(
                sum(tecnicos.mapped('karma_total')) / len(tecnicos),
                2
            )

        response = {
            'total_incidencias': total,
            'incidencias_abiertas': abiertas,
            'incidencias_cerradas': cerradas,
            'incidencias_reabiertas': reabiertas,
            'total_tecnicos': len(tecnicos),
            'media_karma_tecnicos': media_karma
        }

        return request.make_response(
            json.dumps(response),
            headers=[('Content-Type', 'application/json')]
        )
