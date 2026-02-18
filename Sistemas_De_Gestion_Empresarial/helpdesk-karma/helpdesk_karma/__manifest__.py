{
    'name': "Helpdesk Karma",
    'summary': "Gestionar de incidencias con sistema de karma",
    'description': """
                        Gestionar de incidencias con sistema de karma
                        ==============
                   """,  
    'application': True,
    'author': "Victor Aracil",
    'website': "",
    'category': 'Tools',
    'version': '0.1',
    'depends': ['base'],
    'data': [
        'security/ir.model.access.csv',
        'demo/helpdesk_karma_demo.xml',
        'views/helpdesk_karma_incidencia.xml',
        'views/helpdesk_karma_usuario.xml',
        'views/helpdesk_karma_tecnico.xml',
        'views/helpdesk_karma_historial.xml',
        'views/menu.xml',     
    ],
    "demo": [
        "demo/helpdesk_karma_demo.xml",
    ],
}