# Password Manager 

## Password Manager App

<img width="1416" alt="Screenshot 2022-12-05 at 3 12 55 PM" src="https://user-images.githubusercontent.com/86437158/205733922-665b0539-a0d0-4b4e-8986-a37b876abf11.png">

  ## Description

  This application lets users to save all their applications passwords securely in one place. We all carry so many apps on your phone and desktop with so many user credentials that we create and tend to forget so this app solves that problem by letting users to save their passwords in one place. I have placed secure practices in this app to securely register the user, encrypt user passwords and their apps passwords.
  
  Application deployed on AWS: [Live App](http://ngpasswordmanager.s3-website.us-east-2.amazonaws.com/)

  ## Licensing

  [![license](https://img.shields.io/badge/license-MIT-blue)](https://shields.io)

  ## Table of Contents
  - [User Story](#user-story)
  - [Features](#features)
  - [Installation](#installation)
  - [Technology Stack](#technology-stack)
  - [Tests](#tests)
  - [Contributing](#contributing)
  - [Questions](#questions)

  ## User Story

* As a user, I want to be able to be able to register and create my profile. After logging in, I should be able to add my applications name and password. I should also have the ability to update my apps name or passwords. I also want the ability to update any of my profile information either be my username, email or password.

## Features

  * Register and create user profile.

  * Add applications name and its password.

  * Update applications name and its password.

  * Delete applications name and its password.

  * Update user profile info e.g. name, username, email and password.
  
  * Delete user account which will also remove all the saved passwords.

  * App uses JWT to log user into the application.

  * AES256 Encryption is used to save user passwords and applications passwords securely.

  ## Installation

  Follow these simple instructions to set up a local development environment.

1. Clone the repository and install dependencies:

  ```bash
  git clone https://github.com/HassanY99/Password-Manager-App.git
  cd Password-Manager-App
  ```
2. Run Backend-PasswordManager, which is a spring boot maven project.

  ```bash
  Run passwordManager main
  ```
  
3. Run the setup, which takes care of installing dependencies, building packages and ensuring your workspace is dev-ready.

  ```bash
  npm install on passwordManager-frontend angular app
  ng serve
  ```

That's it, you are good to go! Now you can successfully run the app in your local environment! 👾

 ## Technology Stack

  - Angularjs
  - Spring Boot
  - MySql
  - Spring MVC
  - Spring Security - JWT
  - JDBC
  - Bootstrap
  - REST API
  
  ## Tests

  - [Full Video](https://drive.google.com/file/d/1Ys2LGScf0zBTgnlRZZwn5Dw164LzmTkQ/view)
  

  ## Contributing

  Please reach out to me at my email for any question, suggestion or collaboration.

  ## Questions

  You can check me on [HassanY99](https://github.com/HassanY99) and can shoot me an email at hassanyousuf1999@gmail.com.
