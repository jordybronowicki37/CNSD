class BaseConfig(object):
    SQLALCHEMY_ECHO = True
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    RESTX_MASK_SWAGGER = False

class DevelopmentConfig(BaseConfig):
    TESTING=True
    SQLALCHEMY_DATABASE_URI = 'sqlite:///:memory:'

class ExternalDbConfig(BaseConfig):
    SQLALCHEMY_DATABASE_URI = 'mysql+pymysql://cddb_quintor:quintor_pw@cddb-mysql/cddb_quintor'
