from os import environ
import boto3
from botocore.exceptions import ClientError
from aws_xray_sdk.core import patch_all

patch_all()
dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table(environ['CONNECTIONS_TABLE_NAME'])


def lambda_handler(event, context):
    print(f"{event = }")
    connectionId = event['requestContext']['connectionId']

    try:
        table.delete_item(Key={'connectionId': connectionId})
    except ClientError as error:
        print(f"{error = }")
        return {
            'statusCode': 500,
            'body': 'Failed to disconnect!'
        }

    return {
        'statusCode': 200,
        'body': 'Disconnected.'
    }
