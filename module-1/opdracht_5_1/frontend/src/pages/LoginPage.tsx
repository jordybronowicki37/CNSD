import "./LoginPage.scss";
import {useDispatch, useSelector} from "react-redux";
import {userLoginAction} from "../data/reducers/UserReducer.ts";
import {ChangeEvent, FormEvent, useState} from "react";
import {useHistory} from "react-router-dom";
import {StoreTypes} from "../data/DataStore.ts";

export function LoginPage() {
  const dispatch = useDispatch();
  const history = useHistory();
  const user = useSelector<StoreTypes, StoreTypes["user"]>(s => s.user);
  if (user !== null) history.push("/overview");

  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    dispatch(userLoginAction({
      username: formData.username
    }));
    history.push("/overview");
  }

  return (
    <div className="login-page">
      <div className="login-card">
        <h2>Login</h2>
        <p>Login to continue</p>

        <form onSubmit={handleSubmit}>
          <div className="input-container">
            <label htmlFor="username">Username</label>
            <input type="text" id="username" name="username" value={formData.username} onChange={handleInputChange}/>
          </div>
          <div className="input-container">
            <label htmlFor="password">Password</label>
            <input type="password" id="password" name="password" value={formData.password} onChange={handleInputChange}/>
          </div>
          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
}
