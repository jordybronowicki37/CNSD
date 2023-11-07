from __future__ import print_function
import boto3
import json
import base64
from aws_xray_sdk.core import patch_all

# Enable XRay tracing on client libraries
patch_all()

# Initialize the client
s3 = boto3.resource('s3')
sns = boto3.client('sns')
ssm = boto3.client('ssm')

# Read the configuration properties
destBucketname = ssm.get_parameter(Name="/dev/photoProcessor/destBucketname")['Parameter']['Value']
print(f"{destBucketname =}")

snsTopicArn = ssm.get_parameter(Name="/dev/photoProcessor/snsTopicArn")['Parameter']['Value']
print(f"{snsTopicArn =}")


def lambda_handler(event, context):
    # Process each message
    for record in event['Records']:
        # Read the image message from the record (extended message)
        print(f"{record =} {type(record)}")

        body = record["body"]
        print(f"{body =} {type(body)}")

        message = json.loads(body)
        print(f"{message =} {type(message)}")

        bucketName = message[1]["s3BucketName"]
        itemName = message[1]["s3Key"]
        print(f"Extended message body at S3 {bucketName}:{itemName}")

        srcObj = s3.Object(bucketName, itemName)
        base64_img = srcObj.get()['Body'].read()
        decoded_image_data = base64.decodebytes(base64_img)
        print(f"Retreived and decoded the message body")

        # Save the image to the Photo Processor S3 bucket
        destItemName = itemName + ".jpg"
        destObj = s3.Object(destBucketname, destItemName)
        destObj.put(Body=decoded_image_data, ACL='public-read')
        objectUrl = f"https://{destBucketname}.s3.amazonaws.com/{destItemName}"
        print(f"Image processed and stored at {objectUrl}")

        # Send notification to email subscribers
        sns.publish(TopicArn=snsTopicArn, Message=f"Photo processed and available for download.{objectUrl}",
                    Subject="Awesome photo processing done")
        print(f"Notifitation send to email subscribers")

        # Delete the S3 content of the extended message
        srcObj.delete()
        print(f"Extended message body deleted {bucketName}:{itemName}")
