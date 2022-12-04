package com.company.passwordManager.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

        // email ID of  Sender.
        @Value("${email.data.sender}")
        public String sender;

        // using host as localhost
        @Value("${email.data.host}")
        public String host;

        @Value("${email.data.secret}")
        public String password;

        public String getSender() {
                return sender;
        }

        public void setSender(String sender) {
                this.sender = sender;
        }

        public String getHost() {
                return host;
        }

        public void setHost(String host) {
                this.host = host;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}
