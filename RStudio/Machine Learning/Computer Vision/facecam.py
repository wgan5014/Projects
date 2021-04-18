# pip3 install face_recognition prior to use
import sys, cv2, face_recognition, os, base64

"""
 * create face cascade
 * trains to classify something detected as a face or not
 * uses cascading testing system:
 * Test if it passes (a)
 * If (a), then move to (b)
 * Else, no further tests run
 * Passing all tests quantifies the byte block as being a face
 * Convert to grayscale & determine if recognised or not 
 * Uses haar cascades
"""

cascPath = sys.argv[1] # xml file for cascade
faceCascade = cv2.CascadeClassifier(cascPath) # create haar cascade

dbp = sys.argv[2] # get directory of faces (the database)

video_capture = cv2.VideoCapture(0) # set up video capture

"""
Main loop to detect & recognise faces
"""
while True:
    # capture frame-by-frame
    # ret is True/False, frame is the image captured
    ret, frame = video_capture.read()

    # convert frame to gray scale
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    # detect faces
    faces = faceCascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30), flags=cv2.cv.CV_HAAR_SCALE_IMAGE)

    # rectangle around faces
    # x-start, y-start, width, ht.
    for (x, y, w, h) in faces:
        cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)
        # iterate db & match for image in database within tolerance
        for fname in os.listdir(dbp):
            # returns a list of 128 dimensional face encodings read in from each imag file w/in db
            encoding = face_recognition.face_encodings(face_recognition.load_image_file(fname))[0]

            # matching
            if cv2.imencode(".JPEG", frame) == encoding:
                cv2.showtext("{}".format(fname)) # assume filename is the person's name

    # display resulting frame
    cv2.imshow('Video', frame)

    # exit if 'q' entered
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# release capture once done
video_capture.release()
cv2.destroyAllWindows()
