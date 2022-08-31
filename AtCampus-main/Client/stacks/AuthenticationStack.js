import React, {useState} from 'react';
import FrontPage from "../screens/frontPage";
import Login from "../screens/login";
import MakeUserName from "../screens/createUser/makeUserName";
import MakeUserInfo from "../screens/createUser/makeUserInfo";
import MakeUserSchool from "../screens/createUser/makeUserSchool";
import MakeUserPassword from "../screens/createUser/makeUserPassword";
import MakeUserComplete from "../screens/createUser/makeUserComplete";
import {createNativeStackNavigator} from "@react-navigation/native-stack";
import {CreateUserProvider} from "../global/CreateUserContext";
import {TouchableOpacity} from "react-native";
import {Ionicons} from "@expo/vector-icons";


const FrontpageStack = createNativeStackNavigator();

function AuthenticationStack(props) {

	return (
		<CreateUserProvider>
			<FrontpageStack.Navigator>
				<FrontpageStack.Screen
					name="Frontpage"
					component={FrontPage} options={{
					headerShown: false}}
				/>
				<FrontpageStack.Screen
					name="Login"
					component={Login}
					options={({navigation}) => ({
						title: 'Login',
						headerTitleAlign: 'center',
						headerLeft: () => {
							return <TouchableOpacity
								activeOpacity={0.6}
								onPress={() => navigation.goBack()}
							>
								<Ionicons name="chevron-back" size={24} color="black" />
							</TouchableOpacity>
						},
					})}
				/>
				<FrontpageStack.Screen
					name="makeUserName"
					component={MakeUserName}
					options={({navigation}) => ({
						title: 'Name',
						headerTitleAlign: 'center',
						headerLeft: () => {
							return <TouchableOpacity
								activeOpacity={0.6}
								onPress={() => navigation.goBack()}
							>
								<Ionicons name="chevron-back" size={24} color="black" />
							</TouchableOpacity>
						},
					})}
				/>
				<FrontpageStack.Screen
					name="makeUserInfo"
					component={MakeUserInfo}
					options={({navigation}) => ({
						title: 'About',
						headerTitleAlign: 'center',
						headerLeft: () => {
							return <TouchableOpacity
								activeOpacity={0.6}
								onPress={() => navigation.goBack()}
							>
								<Ionicons name="chevron-back" size={24} color="black" />
							</TouchableOpacity>
						},
					})}
				/>
				<FrontpageStack.Screen
					name="makeUserSchool"
					component={MakeUserSchool}
					options={({navigation}) => ({
						title: 'School',
						headerTitleAlign: 'center',
						headerLeft: () => {
							return <TouchableOpacity
								activeOpacity={0.6}
								onPress={() => navigation.goBack()}
							>
								<Ionicons name="chevron-back" size={24} color="black" />
							</TouchableOpacity>
						},
					})}
				/>
				<FrontpageStack.Screen
					name="makeUserPassword"
					component={MakeUserPassword}
					options={({navigation}) => ({
						title: 'Password',
						headerTitleAlign: 'center',
						headerLeft: () => {
							return <TouchableOpacity
								activeOpacity={0.6}
								onPress={() => navigation.goBack()}
							>
								<Ionicons name="chevron-back" size={24} color="black" />
							</TouchableOpacity>
						},
					})}
				/>
				<FrontpageStack.Screen
					name="makeUserComplete"
					component={MakeUserComplete}
					options={({navigation}) => ({
						title: 'Summary',
						headerTitleAlign: 'center',
						headerLeft: () => {
							return <TouchableOpacity
								activeOpacity={0.6}
								onPress={() => navigation.goBack()}
							>
								<Ionicons name="chevron-back" size={24} color="black" />
							</TouchableOpacity>
						},
					})}
				/>
			</FrontpageStack.Navigator>
		</CreateUserProvider>

	);
}

export default AuthenticationStack;
