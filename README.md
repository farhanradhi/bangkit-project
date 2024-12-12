# Sampah.In 
![Screenshot 2024-12-12 215332](https://github.com/user-attachments/assets/c8487538-40b3-4e00-8414-d3b7ab963561)
![Screenshot 2024-12-12 215647](https://github.com/user-attachments/assets/1790b54a-ee20-4f5e-984b-b9add3d106de)

## Our Team C242-PS344
|Name                        |ID            |Path               |
|----------------------------|------------- |-----------------  |
|Farhan Radhi Zuhri          |C315B4KY1409  |Cloud Computing    |
|Rizki Putra Ramadhan        |C315B4KY3915  |Cloud Computing    |
|M. Reihan Alif Albatino D   |M315B4KY2356  |Machine Learning   |
|Laura Nur Meilindy          |M674B4KX2256  |Machine Learning   |
|Ika Widiyanti               |M156B4KX1919  |Machine Learning   |
|Iffah Karimah               |A156B4KX1908  |Mobile Development |
|Wendy                       |A153B4KY4468  |Mobile Development |

## About
Sampah.In is an innovative solution to address the pressing challenges of waste management, which is often overlooked in everyday life. Despite increasing awareness of the importance of environmental conservation, many people still lack the resources or knowledge to manage their waste effectively. Sampah.In is designed to revolutionize this paradigm by leveraging advanced technology and a user-centric approach, providing a practical solution to encourage responsible waste management.

The app offers a seamless experience by combining machine learning algorithms for waste detection with real-time recommendations for nearby waste exchange locations. This feature allows users to take immediate and impactful steps towards recycling or processing their waste without any extra effort. Additionally, an interactive chatbot serves as a valuable tool, offering users precise guidance on waste management practices and educating them about the negative consequences of unmanaged waste.

## Project Goals
* To help people who are struggling to sort waste.
* To support a cleaner environment by encouraging waste recycling.
* To help people find the nearest recycling centers based on their current location.

## Cloud Architecture
![CC_Documentatio](https://github.com/user-attachments/assets/296177d0-9b72-47a6-9ee8-8bedd1bc8847)
![CC_FLowchart_1](https://github.com/user-attachments/assets/d4e0e124-80a3-461f-a6ed-d6b6473581e8)


## Mobile Development Architecture
![Screenshot 2024-12-12 225001](https://github.com/user-attachments/assets/50697523-a1f9-4220-8d5d-c5c2fd2e7726)


## Features
* The main feature of our application is to detect types of waste using image input from users, either directly from the camera or from an image file.
* The second feature is a rule-based chatbot that can help users inquire about the detected types of waste, such as how to manage the waste, how to recycle it, and so on.
* The third feature is the integration of Google Maps, allowing users to find recycling centers in their surrounding area.

## Building Model detection and Chatbot rule-based

### Dataset
we use open sources dataset to training our model, for specific link:
https://drive.google.com/drive/folders/1nRz7Sqkti9eAbrcqpkzcmRBbWWzm_JZM?usp=drive_link 

### Building model
After the model is trained and achieves good results, the model will be saved as an .h5 file to be used as a Flask API, which will later be implemented in the detection application.

### Building Chatbot Rule-based
We use a CSV file containing pre-made questions, topics, and answers. The chatbot will provide responses based on the keywords and topics entered by the user.

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

## Demo Video
To see the Sampah.In video demo, you can see at https://drive.google.com/file/d/1YGbMBPisB7ceFMK9gdbn3iHdSGSi6obN/view
