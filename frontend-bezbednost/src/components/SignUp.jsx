import React from 'react'
import {Formik, Form} from 'formik';
import { TextField } from './TextField';
import * as Yup from 'yup';
import {passwordBlacklist, emailBlacklist} from '../data/Blacklist';
import {Recaptcha} from './Recaptcha';
import { useNavigate } from 'react-router-dom';
import axiosReq from '../config/axiosReq';
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export const SignUp = () => {
    const navigate = useNavigate();

    const validate = Yup.object({
        firstName: Yup.string()
            .min(4, "Too short first name")
            .max(15, "Must be 15 characters or less")
            .required("First name is required"),
        lastName: Yup.string()
            .max(20, "Must be 20 characters or less")
            .required("Last name is required"),
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
        recaptcha:  Yup.string()
            .min(1, 'Confirm that you are a human')
            .required('Confirm that you are a human')
    })
  return (
    <Formik
        initialValues={{
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            confirmPassword: '',
            recaptcha: ''
        }}
        validationSchema={validate}
        onSubmit={async (values) => {
            const {firstName, lastName, email, password} = values;
            try {
                await axiosReq.post("http://localhost:8080/api/registration",{
                firstName,
                lastName,
                email,
                password,
                });
                toast.success("Consumer successfully register", {
                    position: toast.POSITION.TOP_RIGHT,
                });
                setTimeout(() => {
                    navigate('/');
                }, 1000);
                
                    
            } catch (err) {
                toast.error("Failed to register", {
                    position: toast.POSITION.TOP_RIGHT,
                });
            }
        }}
    >
        {formik => (
            <div>
                <h1 className="my-4 font-weight-bold-display-4">Registration</h1>
                <ToastContainer/>
                <Form>
                    <TextField label="First Name" name="firstName" type="text"/>
                    <TextField label="Last Name" name="lastName" type="text"/>
                    <TextField label="Email" name="email" type="email"/>
                    <TextField label="Password" name="password" type="password"/>
                    <TextField label="Confirm password" name="confirmPassword" type="password"/>
                    <Recaptcha name="recaptcha" formik={formik}/>
                    <button className="btn btn-dark mt-3 me-1" type="submit">Register</button>
                    <button className="btn btn-danger mt-3" type="button" onClick={() => navigate("/")}>Login</button>
                </Form>
            </div>
        )}
    </Formik>
    
  )
}
