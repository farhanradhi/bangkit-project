to deploy this API to image docker
```
gcloud builds submit --tag gcr.io/<PROJECT_ID>/<IMAGE_NAME>
```

deploy to cloud run
```
gcloud run deploy <SERVICE_NAME> \
    --image gcr.io/<PROJECT_ID>/<IMAGE_NAME> \
    --platform managed \
    --region <REGION> \
    --allow-unauthenticated
```

