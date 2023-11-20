from aws_xray_sdk.core import patch_all

patch_all()


def lambda_handler(event, context):
    print(f"{event = }")
    return {
        'statusCode': 200,
        'body': 'User did something wrong.'
    }
