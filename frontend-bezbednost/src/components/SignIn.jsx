import React from 'react'
import {Formik, Form} from 'formik';
import { TextField } from './TextField';
import * as Yup from 'yup';
import {passwordBlacklist, emailBlacklist} from '../data/Blacklist';
import { useNavigate } from 'react-router-dom';
import {Recaptcha} from './Recaptcha';
import axiosReq from '../config/axiosReq';
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export const SignIn = () => {
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
        recaptcha:  Yup.string()
            .min(1, 'Confirm that you are a human')
            .required('Confirm that you are a human')
    })
  return (
    <div>
        <ToastContainer/>
        <Formik
            initialValues={{
                email: '',
                password: '',
                recaptcha: ''
            }}
            validationSchema={validate}
            onSubmit={async (values) => {
                console.log(values);
                const {email, password} = values;
                try{
                    await axiosReq.get('http://localhost:8080/api/login', {
                    params : {
                        email,
                        password,
                    }
                    }).then(res => {
                        console.log(res.data);
                        sessionStorage.setItem("token", res.data.token);
                        toast.success("Consumer successfully login", {
                            position: toast.POSITION.TOP_RIGHT,
                        });
                        setTimeout(() => {
                            navigate('accommodation');
                        }, 1000);
                        
                    })
                } catch(err) {
                    toast.error("Failed login", {
                        position: toast.POSITION.TOP_RIGHT,
                    });
                }
            }}
        >
            {formik => (
                <div>
                    <h1 className="my-4 font-weight-bold-display-4">Login</h1>
                    <Form>
                        <TextField label="Email" name="email" type="email"/>
                        <TextField label="Password" name="password" type="password"/>
                        <Recaptcha name="recaptcha" formik={formik}/>
                        <button className="btn btn-dark mt-3 me-1" type="submit">Login</button>
                        <button className="btn btn-danger mt-3 " type="button" onClick={() => navigate("register")}>Register</button>
                        <h6 style={{color:'red', marginTop: "9px"}}>Forgot password? <a href="/reset-password">Reset password</a></h6>
                    </Form>
                </div>
            )}
        </Formik>
    </div>
  )
}
