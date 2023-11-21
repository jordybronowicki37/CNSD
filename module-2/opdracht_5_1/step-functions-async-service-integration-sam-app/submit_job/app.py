from __future__ import print_function
from aws_xray_sdk.core import patch_all

import boto3
import json
import logging
import os

# Enable XRay tracing on client libraries
logger = logging.getLogger()
logger.setLevel(logging.INFO)
patch_all()

# Initialize the clients
client = boto3.client('sqs')

# Read the environment vairables
sqs_queue_url = os.environ['SQS_QUEUE_NAME']
logger.info(sqs_queue_url)


def lambda_handler(event, context):
    logger.info(f"{event} {type(event)}")

    task_token = event["TaskToken"]
    logger.info(task_token)

    sqs_message = {
        "TaskToken": task_token,
        "Message": "You're fantastic!"
    }

    client.send_message(
        QueueUrl=sqs_queue_url,
        MessageBody=json.dumps(sqs_message)
    )
    