import os
from flask import Flask
from flask_restx import Api
from app.main.model import db

def create_app(config_name='default'):
    from app.main.model.album import Album, Track
    from app.main.controller.album_controller import api as album_namespace
    from app.main.controller.track_controller import api as track_namespace

    config = {
        'development': 'app.config.DevelopmentConfig',
        'mysql': 'app.config.ExternalDbConfig'
    }

    api = Api(title='CDDB MUSIC APP API',
              version='1.0',
              description='music app backend',
              prefix='/cddb',
              doc='/cddb/'
              )

    api.add_namespace(album_namespace)
    api.add_namespace(track_namespace)

    app = Flask(__name__)

    # load application configuration
    app_environment = os.environ.get('APP_ENVIRONMENT')
    config_name = config.get('development' if app_environment is None else app_environment, config.get('development'))
    app.config.from_object(config_name)

    db.init_app(app)
    api.init_app(app)

    # create tables in the database if they do not exist
    app.app_context().push()
    db.create_all()
    return app
