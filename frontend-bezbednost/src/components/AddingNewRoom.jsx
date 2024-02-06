import React from 'react';
import './AddingNewRoom.css'
import {Formik, Form} from 'formik';
import { TextField } from './TextField';
import * as Yup from 'yup';
import { useNavigate } from 'react-router-dom';
import axiosReq from '../config/axiosReq';
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export const AddingNewRoom = () => {
    const navigate = useNavigate();
    
    const validate = Yup.object({
        roomName: Yup.string()
            .min(4, "Too short room name")
            .max(15, "Must be 15 characters or less")
            .required("Room name is required"),
        roomType: Yup.string()
            .min(4, "Too short room type")
            .max(15, "Must be 15 characters or less")
            .required("Room type is required"),
        numberOfAdults: Yup.number()
            .min(1, "You have to add minimum 1 adults")
            .max(5, "Must be 5 or less")
            .required("Number of adults is required"),
        numberOfChild: Yup.number()
            .min(0, "You have to add minimum 0 child")
            .max(5, "Must be 5 or less")
            .required("Number of child is required")
    })
  return (
    <div>
        <div className='container'>
            <ToastContainer/>
            <div className='form'>
            <Formik
                initialValues={{
                    roomName: '',
                    roomType: '',
                    numberOfAdults: 0,
                    numberOfChild: 0
                }}
                validationSchema={validate}
                onSubmit={async (values, {resetForm}) => {
                    const {roomName, roomType, numberOfAdults, numberOfChild} = values;
                    try{
                        await axiosReq.post('http://localhost:8080/api/accommodation', {
                            roomName,
                            roomType,
                            numberOfAdults,
                            numberOfChild
                        }).then(res => {
                            toast.success("Accommodation successfully added", {
                                position: toast.POSITION.TOP_RIGHT,
                            });
                            resetForm();
                        })
                    } catch(err) {
                        toast.error("Failed to add accommodation", {
                            position: toast.POSITION.TOP_RIGHT,
                        });
                    }
                }}
            >
                {formik => (
                    <div>
                        <h2 className='heading2'>For adding new type of accommodation fill form!</h2>
                        <Form>
                            <TextField label="Room name" name="roomName" type="text" style={{textAlign: "center"}}/>
                            <TextField label="Room type" name="roomType" type="text" style={{textAlign: "center"}}/>
                            <TextField label="Number of adults" name="numberOfAdults" type="number" style={{textAlign: "center"}}/>
                            <TextField label="Number of child" name="numberOfChild" type="number" style={{textAlign: "center"}}/>
                            <button className='btnAdd' type="submit">Add room</button>
                        </Form>
                    </div>
                )}
            </Formik>
                
            </div>
        </div>
    </div>
  )
}
