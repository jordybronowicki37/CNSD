import json
from aws_xray_sdk.core import patch_all

patch_all()


def lambda_handler(event, context):
    return {
        "statusCode": 200,
        "body": json.dumps({
            "message": "hello world",
        }),
    }
