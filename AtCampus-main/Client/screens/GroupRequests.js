import React, {useEffect, useState} from 'react'
import {FlatList, StyleSheet, Text, TouchableOpacity, View} from "react-native";
import GroupRequestCard from "../components/GroupRequestCard";
import {addUserToGroupByGroupRequest, deleteGroupRequest, getAllGroupRequests} from "../services/GroupService";
import {AntDesign, Feather} from "@expo/vector-icons";
import Toast from "react-native-toast-message";

const groupRequestData = [
    {
        id: 1,
        userImage: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        school: "Høyskolen Kristiania",
        program: "Programmering",
        text: "wow plx let me join",
        date: "5 March"
    },
    {
        id: 2,
        userImage: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        school: "Høyskolen Kristiania",
        program: "Frontend",
        text: "wow plx let me join plx let me join plx let me join let me join let me join let me join",
        date: "3 March"
    }
]

function Separator() {
    return <View style={styles.separator}/>
}

export default function GroupRequests({navigation, route}){
    const [dummyData, setDummyData] = useState(groupRequestData)
    const [groupRequests, setGroupRequests] = useState([])
    const {group} = route.params

    function getGroups() {
        getAllGroupRequests(group.id)
            .then((res) => {
                setGroupRequests(res.data)
            })
            .catch((err) => {
                console.log("Error in GroupRequests: " + err)
            })
    }

    function handleAddClick(requestId){
        addUserToGroupByGroupRequest(requestId)
            .then((res) => {
                getGroups()
            })
            .catch((err) => {
                console.log("Error in GroupRequests: " + err)
            })
    }

    useEffect(() => {
        getGroups()
    }, [])

    //it has changed
    return (
        <View style={{flex: 1}}>
            <FlatList
                contentContainerStyle={styles.groupRequests}
                data={groupRequests}
                ItemSeparatorComponent={Separator}
                stickyFooterIndices={[0]}
                renderItem={({item}) =>
                    // <GroupRequestCard
                    //     style={{
                    //         marginHorizontal: 20,
                    //         marginVertical: 2,
                    //     }}
                    //     key={item.id}
                    //     requestId={item.id}
                    //     userImage={item.userEntity.userProfileImage}
                    //     school={item.userEntity.school}
                    //     program={item.userEntity.program}
                    //     text={item.userEntity.firstName + " " + item.userEntity.lastName}
                    //     message={item.message}
                    //     handleClick={() => {
                    //         handleAddClick(item.id)
                    //     }}
                    // />

                    <Request
                        id={item.id}
                        message={item.message}
                        handler={handleAddClick}
                        getGroups={getGroups}
                    />
                }
            />

        </View>
    )
}

function Request({id, message, handler, getGroups}) {
    return (
        <View style={request.container}>
            <Text style={request.message}>{message}</Text>

            <View style={request.buttonWrapper}>
                <TouchableOpacity
                    activeOpacity={0.6}
                    onPress={() => handler(id)}
                    style={[request.button, {marginBottom: 10}]}
                >
                    <Feather style={request.checkmark} name={"check"} color={"black"} size={28} />
                </TouchableOpacity>

                <TouchableOpacity
                    activeOpacity={0.6}
                    onPress={() => {
                        deleteGroupRequest(id)
                            .then(() => {
                                Toast.show({
                                    type: "success",
                                    text1: "Removed request",
                                })

                                getGroups();
                            })
                            .catch(err => {
                                console.log("Could not remove request", err);

                                Toast.show({
                                    type: "error",
                                    text1: "Could not remove request",
                                })
                            })
                    }}
                    style={request.button}
                >
                    <AntDesign name="close" size={26} color="black" />
                </TouchableOpacity>
            </View>
        </View>
    )
}

const request = StyleSheet.create({
    container: {
        display: "flex",
        flexDirection: "row",
        backgroundColor: "white",
        padding: 20,
        borderRadius: 20,
        margin: 10,
    },
    buttonWrapper: {
        display: "flex",
    },
    message: {
        flex: 1,
        textAlignVertical: "top",
    },
    checkmark: {
    },
    button: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "rgb(230, 230, 230)",
        borderRadius: 25,
        width: 50,
        height: 50,
        marginLeft: 10,
    }
})

const styles = StyleSheet.create({
    requestContainer:{

    },
    separator: {
        height: 10,
    },
    groupRequests: {
        marginBottom: 15
    }
})
