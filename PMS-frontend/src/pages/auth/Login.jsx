import { useState } from "react";
import { useNavigate } from "react-router-dom";

import Input from "../../components/common/Input/Input";
import Button from "../../components/common/Button/Button";

import authService from "../../services/authService";
import { saveToken } from "../../utils/auth";
import { useAuth } from "../../context/AuthContext";

import "./Login.css";
import { toast } from "react-toastify";

function Login() {

    const navigate = useNavigate();
    const { login } = useAuth();

    const [loginData, setLoginData] = useState({
        email: "",
        password: "",
    });

    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {

        setLoginData(prev => ({
            ...prev,
            [e.target.name]: e.target.value,
        }));
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        if (!loginData.email.trim() || !loginData.password.trim()) {
            toast.warning("Please enter Email and Password.");
            return;
        }

        try {

            setLoading(true);

            const response = await authService.login(loginData);

            if (response?.token) {

                saveToken(response.token);

                login({
                    userId: response.userId,
                    name: response.name,
                    email: response.email,
                    role: response.role,
                    imageUrl: response.imageUrl,
                });

                navigate("/dashboard", {
                    replace: true,
                });

            } else {

                toast.error("Login failed. Token not received.");
            }

        } catch (error) {

            console.error(error);

            const message =
                error?.response?.data?.message ||
                "Unable to connect to server.";

            toast.error(message);

        } finally {

            setLoading(false);
        }
    };

    return (

        <div className="login-container">

            <div className="login-card">

                <h1>Political Management System</h1>

                <p>Sign in to continue</p>

                <form onSubmit={handleSubmit}>

                    <Input
                        label="Email"
                        type="email"
                        name="email"
                        placeholder="Enter Email"
                        value={loginData.email}
                        onChange={handleChange}
                    />

                    <Input
                        label="Password"
                        type="password"
                        name="password"
                        placeholder="Enter Password"
                        value={loginData.password}
                        onChange={handleChange}
                    />

                    <Button
                        type="submit"
                        disabled={loading}
                    >
                        {loading ? "Logging In..." : "Login"}
                    </Button>

                </form>

            </div>

        </div>
    );
}

export default Login;