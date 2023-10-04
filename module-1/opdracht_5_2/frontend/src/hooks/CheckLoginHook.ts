import {useSelector} from "react-redux";
import {StoreTypes} from "../data/DataStore.ts";
import {useHistory} from "react-router-dom";
import {useEffect} from "react";

export function useCheckForUserNotLoggedIn() {
    const user = useSelector<StoreTypes, StoreTypes["user"]>(s => s.user);
    const history = useHistory();
    useEffect(() => {
        if (user === null) history.push("/login");
    });
}

export function useCheckForUserAlreadyLoggedIn() {
    const user = useSelector<StoreTypes, StoreTypes["user"]>(s => s.user);
    const history = useHistory();
    useEffect(() => {
        if (user !== null) history.push("/overview");
    });
}