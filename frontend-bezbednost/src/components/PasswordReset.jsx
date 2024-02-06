import React from 'react';
import {Formik, Form} from 'formik';
import { TextField } from './TextField';
import * as Yup from 'yup';
import {passwordBlacklist, emailBlacklist} from '../data/Blacklist';
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';

export const PasswordReset = () => {
    const navigate = useNavigate();
    const validate = Yup.object({
        email: Yup.string()
            .email("Email is invalid")
            .notOneOf(emailBlacklist, "Email is in the black list")
            .lowercase()
            .required("Email is required"),
        password: Yup.string()
            .matches(/(?=.*[a-z])/, "One lowercase required")
            .matches(/(?=.*[A-Z])/, "One uppercase required")
            .matches(/(?=.*[0-9])/, "One number required")
            .min(6, "Password must be at least 6 characters")
            .notOneOf(passwordBlacklist, "Password is in the black list")
            .required("Password is required"),
        confirmPassword: Yup.string()
            .oneOf([Yup.ref("password"), null], "Password")
            .required("Confirm password is required"),
    })

  return (
    <div>
        <ToastContainer/>
        <Formik
            initialValues={{
                email: '',
                password: '',
                confirmPassword: ''
            }}
            validationSchema={validate}
            onSubmit={async (values) => {
                console.log(values);
                const {email, password} = values;

                try {
                    await axios.post("http://localhost:8080/api/reset-password",{
                    email,
                    password,
                    });
                    toast.success("Korisniku je uspesno prosledjen mejl za verifikaciju nove lozinke", {
                        position: toast.POSITION.TOP_CENTER,
                    });
                    setTimeout(() => {
                        navigate('/');
                    }, 1000);
                } catch (err) {
                    toast.error("Niste uneli ispravne podatke pokusajte ponovo", {
                        position: toast.POSITION.TOP_CENTER,
                    });
                }
            }}
        >
            {formik => (
                <div>
                    <h1 className="my-4 font-weight-bold-display-4" style={{display: 'flex', justifyContent: 'center'}}>Reset your old password</h1>
                    <Form>
                        <TextField label="Email" name="email" type="email"/>
                        <TextField label="New Password" name="password" type="password"/>
                        <TextField label="Confirm new password" name="confirmPassword" type="password"/>
                        <button className="btn btn-primary mt-3" type="submit" style={{width: "-moz-available"}}>Reset password</button>
                        <h6 style={{color:'red', marginTop: "9px"}}>You know your old password! <a href="/">Login</a></h6>
                    </Form>
                </div>
            )}
        </Formik>
    </div>
  )
}
