S3_BUCKET="cnsd-jb-test"

echo "Copy content to the S3 bucket ${S3_BUCKET}:"
aws s3 cp index.html s3://${S3_BUCKET}/index.html
