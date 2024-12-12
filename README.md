## Setup Installation in Google Cloud Platform

1. open Google Cloud Console. you can open Google cloud console via link : https://console.cloud.google.com
2. Activate Google Shell on top right corner of the console.
3. Set project ID
   ```
   gcloud config set project PROJECT_ID
   ```

4. Enable necessary API in Google Cloud Platform such as Cloud Run API, Firebase API, App Engine API, etc
   ```
   gcloud services enable run.googleapis.com
   ```

5. Clone Repository via command line
   ```
   git clone -b Cloud Computing https://github.com/farhanradhi/bangkit-project.git
   ```

6. Open folder
   ```
   cd bangkit-project
   ```
   
7. build docker image and push artifact registry 
   ```
   gcloud builds submit --tag gcr.io/<PROJECT_ID>/<IMAGE_NAME>
   ```

8. Deploy to Cloud Run with desired location
   ```
   gcloud run deploy <SERVICE_NAME> \
    --image gcr.io/<PROJECT_ID>/<IMAGE_NAME> \
    --platform managed \
    --region <REGION> \
    --allow-unauthenticated
   ```

9. Deploy model API to app engine
    ```
    gcloud app deploy
    ```

10. wait until deployment process to complete. Cloud Run and App engine will automatically create url for the API
