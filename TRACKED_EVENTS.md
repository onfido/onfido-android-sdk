### Tracked events

Below is the list of potential events currently tracked by the analytics event listener:

```
WELCOME - User reached the “Welcome” screen 

USER_CONSENT - User reached the “consent” screen
 
DOCUMENT_CAPTURE - User reached the “document capture” screen (for one-sided document) 
 
DOCUMENT_CAPTURE_FRONT - User reached the “document capture” screen for the front side (for two-sided document) 
 
DOCUMENT_CAPTURE_BACK - User reached the “document capture” screen for the back side (for two-sided document) 
 
DOCUMENT_CAPTURE_CONFIRMATION - User reached the “document confirmation” screen (for one-sided document) 
 
DOCUMENT_CAPTURE_CONFIRMATION_FRONT - User reached the “document confirmation” screen for the front side (for two-sided document) 
 
DOCUMENT_CAPTURE_CONFIRMATION_BACK - User reached the “document confirmation” screen for the back side (for two-sided document) 
 
DOCUMENT_UPLOAD - User's document is uploading 
 
FACIAL_INTRO - User reached the “selfie intro” screen 
 
FACIAL_CAPTURE - User reached the “selfie capture” screen 
 
FACIAL_CAPTURE_CONFIRMATION - User reached the “selfie confirmation” screen 
 
FACIAL_UPLOAD - User's selfie is uploading 
 
VIDEO_FACIAL_INTRO - User reached the “liveness intro” screen 
 
VIDEO_FACIAL_CAPTURE - User reached the “liveness video capture” screen 
 
VIDEO_FACIAL_CAPTURE_STEP_1 - User reached the 1st challenge during “liveness video capture”, challenge_type can be found in the event properties 
 
VIDEO_FACIAL_CAPTURE_STEP_2 - User reached the 2nd challenge during “liveness video capture”, challenge_type can be found in the event properties
 
VIDEO_FACIAL_CAPTURE_CONFIRMATION - User reached the “liveness video confirmation” screen 
 
VIDEO_FACIAL_UPLOAD - User's liveness video is uploading

MOTION_FACIAL_INTRO - User reached the "motion intro" screen

MOTION_FACIAL_ALIGNMENT - User reached the "motion alignment" screen

MOTION_FACIAL_CAPTURE - User reached the "motion capture" screen

MOTION_FACIAL_NO_FACE_DETECTED - User's face was not detected

MOTION_FACIAL_CAPTURE_ERROR_TIMEOUT - User's motion capture timed out

MOTION_FACIAL_CAPTURE_ERROR_TOO_FAST - User performed the motion headturn too fast

MOTION_FACIAL_UPLOAD - User's motion capture is uploading

MOTION_FACIAL_UPLOAD_COMPLETED - User's motion capture finished uploading

MOTION_FACIAL_CONNECTION_ERROR - User was presented the "motion connection error" screen during upload
```
