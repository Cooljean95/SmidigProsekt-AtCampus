import {StyleSheet, Text, View} from "react-native";
import { StatusBar } from "expo-status-bar";


export default function ShowUserInfo({userInfo, userText}){

    return (
            <View style={styles.viewWrap}>
                <Text style={styles.widthText}>{userText}</Text>
                <Text style={styles.widthValue}>{userInfo}</Text>
            </View>
    );
}



const styles = StyleSheet.create({
    viewWrap:{
        borderWidth: 1,
        borderColor: "#f3f2f2",
        paddingVertical: 15,
        paddingHorizontal: 10,
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center"
    },
    widthText:{
        width: 100
    },
    widthValue: {
        textAlign: "right",
        width: 200
    }
});