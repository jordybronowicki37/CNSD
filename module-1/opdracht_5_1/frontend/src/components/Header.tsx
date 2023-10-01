import "./Header.scss";
import {useDispatch, useSelector} from "react-redux";
import {StoreTypes} from "../data/DataStore.ts";
import {userLogoutAction} from "../data/reducers/UserReducer.ts";
import {Link} from "react-router-dom";

export function Header() {
  const user = useSelector<StoreTypes, StoreTypes["user"]>(s => s.user);
  const dispatch = useDispatch();

  return (
    <header>
      <h1>CNSD Account</h1>

      {user === null ?
        <nav>
          <Link to="/login">Login</Link>
        </nav> :
        <nav>
          <Link to="/overview">Overview</Link>
          <Link to="/login" onClick={() => dispatch(userLogoutAction())}>Logout</Link>
        </nav>
      }
    </header>
  );
}