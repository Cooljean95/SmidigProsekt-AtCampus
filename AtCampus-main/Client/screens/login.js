import {
    Button,
    StyleSheet,
    Text,
    View,
    TouchableOpacity,
    TextInput,
    KeyboardAvoidingView,
    ScrollView,
    FlatList
} from "react-native";
import {StatusBar} from "expo-status-bar";
import {useState} from "react";
import {useDispatch} from "react-redux";
import {login} from "../actions/auth";
import LoginInput from "../components/LoginInput";

export default function Login({navigation}) {
	const dispatch = useDispatch();

	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");

	function attemptLogin() {
		dispatch(login(email, password))
	}

    return (
        <KeyboardAvoidingView style={styles.container}>
            <ScrollView style={styles.scrollWrapper}>
                <StatusBar style="auto" />
	            
                <LoginInput
	                title={"Email"}
	                value={email}
	                onChangeText={(val => setEmail(val))}
	                keyboardType={"default"}
                />

                <LoginInput
	                title={"Password"}
	                value={password}
	                onChangeText={(val => setPassword(val))}
	                keyboardType={"default"}
	                secureTextEntry={true}
                />

                <TouchableOpacity>
                    <Text style={styles.forgot}>Forgot password?</Text>
                </TouchableOpacity>

	            <View style={styles.infoWrapper}>
		            <Text style={styles.textService}>
			            <Text>By continuing, you agree to our</Text>
			
			            <TouchableOpacity>
				            <Text style={styles.terms}> Terms of Service </Text>
			            </TouchableOpacity>
			
			            <Text>and</Text>
			
			            <TouchableOpacity>
				            <Text style={styles.policy}> Privacy Policy.</Text>
			            </TouchableOpacity>
		            </Text>
	            </View>

                <TouchableOpacity
	                style={styles.button}
	                onPress={() => attemptLogin()}
                >
                    <Text style={styles.buttonText}>Login</Text>
                </TouchableOpacity>
            </ScrollView>
        </KeyboardAvoidingView>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
	    display: "flex",
	    alignItems: "center",
	    justifyContent: "center",
    },
	scrollWrapper: {
        padding: 20,
	},
	infoWrapper: {
        marginTop: 100,
	},
    login: {
        fontSize: 25,
        marginBottom: 10
    },
    input:{
        alignSelf: "center",
        height: 50,
        width: 300,
        margin: 5,
        marginTop: 40,
        borderWidth: 1,
        borderRadius: 10,
        padding: 10,
        borderColor: "#d3d3d3"
    },
    forgot:{
        fontSize: 15,
        fontWeight: "bold",
        color: "#7C7FCA",
        textAlign: "left",
    },
    textService:{
    	textAlign: "center",
    },
    button:{
    	alignSelf: "center",
        display: "flex",
	    alignItems: "center",
	    justifyContent: "center",
        width: 300,
        height: 50,
        backgroundColor: "#7C7FCA",
        marginTop: 20,
        borderRadius: 25
    },
    buttonText:{
        color:"#fff",
        fontSize: 18,
    },
    policy:{
        color: "#7C7FCA",
    },
    terms:{
        color: "#7C7FCA",
    }
});
