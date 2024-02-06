import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const SecurityRoute = (props) => {
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const userToken = sessionStorage.getItem('token');
        if (!userToken || userToken === 'undefined') {
            setIsLoggedIn(false);
            return navigate("/");
        }
        setIsLoggedIn(true);
    }, [isLoggedIn, navigate]);

    return (
        <div>
            {
                isLoggedIn ? props.children : null
            }
        </div>
    );
}

export default SecurityRoute;