import React, {useRef} from 'react'
import ReCaptcha from 'react-google-recaptcha';
import { ErrorMessage, useField } from 'formik';

export const Recaptcha = ({formik, ...props}) => {
    const reCaptchaRef = useRef(null);
    const [field] = useField(props);
    function onChangeRecaptcha() {
        const token = reCaptchaRef.current.getValue();
        formik.setFieldValue('recaptcha', token);
    }
  return (
    <div>
         <ReCaptcha
            ref={reCaptchaRef}
            sitekey="6LdR_UgpAAAAAERQGH7Xvn8lEhC3kHhTYcozLcgN"
            value={formik.values.recaptcha}
            onChange={onChangeRecaptcha}
        />
        <ErrorMessage component="div" name={field.name} className="error" />
    </div>
  )
}
