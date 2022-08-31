import {View, StyleSheet, Text, TouchableOpacity} from "react-native";
import {CreateUserContext} from "../global/CreateUserContext";
import {useContext} from "react";

const LoginButton = ({ title, onPress}) => {

    return(
        <View>
            <TouchableOpacity
                style={styles.button}
                title={title}
                onPress={onPress}
            >
                <Text style={styles.next}>{title}</Text>
            </TouchableOpacity>
        </View>
    )

}

const styles = StyleSheet.create({
    button: {
        alignSelf: "center",
        alignItems: "center",
        backgroundColor: "#7C7FCA",
        borderRadius: 30,
	    marginTop: 20,
	    width: "100%",
	    paddingVertical: 15,
	    paddingHorizontal: 30,
    },
    next: {
        color: "#ffffff",
        fontWeight: "bold",
        fontSize: 15
    }
});


export default LoginButton
