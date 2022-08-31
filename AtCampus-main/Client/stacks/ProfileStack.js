import {createNativeStackNavigator} from "@react-navigation/native-stack";
import ProfileScreen from "../screens/ProfileScreen";
import ChangePasswordScreen from "../screens/ChangePasswordScreen";
import {TouchableOpacity} from "react-native";
import {Ionicons} from "@expo/vector-icons";

const Stack = createNativeStackNavigator();

function ProfileStack() {
	return (
		<Stack.Navigator>
			<Stack.Screen
				name={"Your Profile"}
				component={ProfileScreen}
			/>
			<Stack.Screen
				name={"Change Password"}
				component={ChangePasswordScreen}
				options={({navigation}) => ({
					headerLeft: () => {
						return <TouchableOpacity
							activeOpacity={0.6}
							onPress={() => navigation.goBack()}
						>
							<Ionicons name="chevron-back" size={24} color="black" />
						</TouchableOpacity>
					}
				})}
			/>
		</Stack.Navigator>
	)
}

export default ProfileStack;
