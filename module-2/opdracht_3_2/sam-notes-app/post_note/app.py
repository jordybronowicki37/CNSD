import datetime
import decimal
import json
import boto3
from aws_xray_sdk.core import patch_all
from sqlalchemy import text, create_engine

patch_all()

ssm = boto3.client('ssm')
username = ssm.get_parameter(Name="/dev/database/notes/admin/username", WithDecryption=True)['Parameter']['Value']
password = ssm.get_parameter(Name="/dev/database/notes/admin/password", WithDecryption=True)['Parameter']['Value']
database_url = ssm.get_parameter(Name="/dev/database/notes/url", WithDecryption=True)['Parameter']['Value']
database_name = ssm.get_parameter(Name="/dev/database/notes/name")['Parameter']['Value']
port = 5432

connection_string = f"postgresql://{username}:{password}@{database_url}:{port}/{database_name}"

engine = create_engine(connection_string)


def alchemy_encoder(obj):
    """JSON encoder function for SQLAlchemy special classes."""
    if isinstance(obj, datetime.date):
        return obj.isoformat()
    elif isinstance(obj, decimal.Decimal):
        return float(obj)


def lambda_handler(event, context):
    print(f"{event =} {type(event)}")

    note_text = json.loads(event.get('body')).get('text')

    with engine.connect() as connection:
        query = text(f"INSERT INTO public.notes (text) VALUES (:x) RETURNING *")
        query = query.bindparams(x=note_text)
        result = connection.execute(query)

    print(f"{result =}")
    notes = json.dumps([dict(row) for row in result], default=alchemy_encoder)

    return {
        'statusCode': 200,
        'body': notes,
        'headers': {
            'content-type': 'application/json'
        },
        "isBase64Encoded": False
    }
