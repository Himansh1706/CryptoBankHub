import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css'],
})
export class LandingPageComponent implements OnInit {
  constructor(private router:Router) {}

  loginModel = {
    email:'',
    password:''
  }

  ngOnInit(): void {
    const createBtn = document.getElementById('create-new-account-btn');
    const loginMenu = document.getElementById('login-menu');
    const signUpMenu = document.getElementById('sign-up-menu');
    const alreadyHaveAccountBtn = document.getElementById(
      'already-has-account-btn'
    );

    createBtn?.addEventListener('click', () => {
      loginMenu?.classList.add('inactive');
      loginMenu?.classList.remove('active');
      signUpMenu?.classList.add('active');
      signUpMenu?.classList.remove('inactive');
    });

    alreadyHaveAccountBtn?.addEventListener('click', () => {
      loginMenu?.classList.add('active');
      loginMenu?.classList.remove('inactive');
      signUpMenu?.classList.add('inactive');
      signUpMenu?.classList.remove('active');
      this.alreadyHaveAccount();
    });
  }

  sendOtp(){
    const sendOtpBtn = document.getElementById('send-otp-btn');
    const verifyOtpBtn = document.getElementById('verify-otp-btn');
    const otpInputField = document.getElementById('otp-input-field');

    otpInputField?.classList.add('active');
    otpInputField?.classList.remove('inactive');
    sendOtpBtn?.classList.add('inactive')
    sendOtpBtn?.classList.remove('active')
    verifyOtpBtn?.classList.add('active')
    verifyOtpBtn?.classList.remove('inactive')
  }

  alreadyHaveAccount(){
    const sendOtpBtn = document.getElementById('send-otp-btn');
    const verifyOtpBtn = document.getElementById('verify-otp-btn');
    const otpInputField = document.getElementById('otp-input-field');
    otpInputField?.classList.add('inactive');
    otpInputField?.classList.remove('active');
    sendOtpBtn?.classList.add('active')
    sendOtpBtn?.classList.remove('inactive')
    verifyOtpBtn?.classList.add('inactive')
    verifyOtpBtn?.classList.remove('active')
  }
  
  onLoginSubmit(){
    console.log(this.loginModel)
  }

  verifyOtp(){
    this.router.navigate(['/home']);
  }

}
