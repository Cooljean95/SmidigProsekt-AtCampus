import React from 'react';
import HomeScreen from "../screens/HomeScreen";
import ActivitiesScreen from "../screens/ActivitiesScreen";
import {createBottomTabNavigator} from "@react-navigation/bottom-tabs";
import ProfileScreen from "../screens/ProfileScreen";
import {AntDesign, Ionicons} from "@expo/vector-icons";
import GroupStack from "./GroupStack";
import ProfileStack from "./ProfileStack";
import GroupScreen from "../screens/GroupScreen";

const Tab = createBottomTabNavigator();

function HomeStack(props) {
	return (
		<Tab.Navigator
			screenOptions={({route}) => ({
				tabBarIcon: ({focused, color, size}) => {
					let iconName;
					
					if (route.name === "Home") {
						iconName = focused
							? "home"
							: "home-outline";
					} else if (route.name === "Activities") {
						iconName = focused
							? "book"
							: "book-outline";
					} else if (route.name === "Groups") {
						iconName = focused
							? "people"
							: "people-outline";
					} else if (route.name === "Me") {
						iconName = focused
							? "person"
							: "person-outline";
					}
					
					return <Ionicons name={iconName} size={size} color={color}/>;
				},
				tabBarActiveTintColor: "#7c7fca",
			})}
		>
			<Tab.Screen name="Home" component={HomeScreen} options={{
				headerShown: false,
			}}/>
			<Tab.Screen name="Activities" component={ActivitiesScreen} />
			<Tab.Screen
				name="Groups"
				component={GroupStack}
				options={{
					headerShown: false,
				}}
			/>
			<Tab.Screen
				name="Me"
				component={ProfileStack}
				options={{
					headerShown: false,
				}}
			/>
		</Tab.Navigator>
	);
}

export default HomeStack;
