import { NavigationContainer } from '@react-navigation/native';
import HomeStack from "./stacks/HomeStack";
import AuthenticationStack from "./stacks/AuthenticationStack";
import {StatusBar} from "react-native";
import {BottomSheetModalProvider} from "@gorhom/bottom-sheet";
import {Provider, useDispatch, useSelector} from "react-redux";
import store from "./store";
import Toast from 'react-native-toast-message';
import {useEffect} from "react";
import {check} from "./actions/auth";

import 'intl';
import 'intl/locale-data/jsonp/en';

function Juice() {
	const {isLoggedIn} = useSelector(state => state.auth);
	const dispatch = useDispatch();
	
	useEffect(() => {
		dispatch(check())
	}, []);
	
	return (
		<BottomSheetModalProvider>
			<NavigationContainer>
				{isLoggedIn ?
					<HomeStack/>
					:
					<AuthenticationStack/>
				}
				
				<StatusBar barStyle={"dark-content"}/>
			</NavigationContainer>
		</BottomSheetModalProvider>
	)
}

export default function App() {
    return (
    	<Provider store={store}>
		    <Juice/>
		    
		    <Toast/>
	    </Provider>
    );
}
