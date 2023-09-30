import './App.css'
import {Header} from "./components/Header.tsx";
import {Redirect, Route} from "react-router-dom";
import {LoginPage} from "./pages/LoginPage.tsx";

export function App() {
  return (
    <>
        <Header/>
        <Route exact path="/"><Redirect to="/login"/></Route>
        <Route path="/login" component={LoginPage}/>
    </>
  )
}
