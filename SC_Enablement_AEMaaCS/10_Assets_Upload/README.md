
# Asset Micro Service - AEM Upload 

#### Overview

In legacy AEM Assets, a single post request to createAsset servlet is enough for uploading files. Newer versions of AEM use direct binary access, which requires a more involved algorithm.

This tool is provided for making uploading easier, and can be used as a command line executable or required as a Node.js module.

![1.png](./images/1.png) 

#### Usage

This library supports uploading files to a target instance, while providing support for monitoring transfer progress, cancelling transfers, and other features.

To install the and use the command locally

> ``` npm install -g @adobe/aio-cli-plugin-aem ```

> ```  aio-aem (-v|--version|version) ```

> ``` aio-aem --help ```

#### Upload asset binaries to AEM

1. Navigate to AEM as a Cloud Service Instance.
2. Go to Tools > Security > Users
3. Create a new user, set his password and set user group to `administrators`.
4. Download and extract [Demo_Images.zip](./images/Demo_Images.zip)
5. Open command prompt/terminal
6. Run the following command
    > aio-aem aem:upload path_to_Demo_Images_folder -h <AEM as Cloud Service Instance URL> -c username:password

    e.g.
    > aio-aem aem:upload Demo_Images -h https://author-p9357-e17906.adobeaemcloud.com -c ksaner:password

7. Following output should be displayed on the terminal window
    > ![2.PNG](./images/2.PNG)

    > Verify the uploadURIs pointing to azure blob storage

    > ![3.PNG](./images/4.PNG)
8. Go to Assets > Files > aem-upload-xxxxx and verify the uploaded assets

    > ![3.PNG](./images/3.PNG)







