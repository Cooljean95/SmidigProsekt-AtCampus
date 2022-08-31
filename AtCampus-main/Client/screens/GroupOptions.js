import React, {Component, useEffect, useState} from 'react';
import {
    FlatList,
    Image,
    StyleSheet,
    Text,
    TouchableOpacity,
    View
} from 'react-native';

import ContentSeparator from "../components/ContentSeparator";
import MemberCard from "../components/MemberCard";
import GroupDetails from "../components/GroupDetails";
import * as ImagePicker from "expo-image-picker";
import {getUsersFromGroup} from "../services/GroupService";
import GroupPage from "../components/GroupPage";

const memberListData = [
    {
        id: 1,
        text: "Naruto",
        profileImage: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        date: "6 March"
    },
    {
        id: 2,
        text: "Billy",
        profileImage: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        date: "10 March"
    },
    {
        id: 3,
        text: "Billy",
        profileImage: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        date: "10 March"
    },
    {
        id: 4,
        text: "Billy",
        profileImage: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        date: "10 March"
    }
]


function MemberSeparator() {
    return <View style={{height: 10}}/>
}

export default function GroupOptions({navigation, route}) {

    const [image, setImage] = useState(null);
    const [memberList, setMemberList] = useState(memberListData)
    const {group} = route.params
    const [users, setUsers] = useState([])


    useEffect(() => {
        getUsersFromGroup(group.id).then((res) =>{
            setUsers(res.data)
        }).catch((err) =>{
            console.log(err.toString())
        })
    }, [])

    async function onChangeProfilePicture() {
        // No permissions request is necessary for launching the image library
        let result = await ImagePicker.launchImageLibraryAsync({
            mediaTypes: ImagePicker.MediaTypeOptions.Images,
            allowsEditing: true,
            aspect: [1, 1],
            quality: 1,
        });

        console.log(result);

        if (!result.cancelled) {
            setImage(result.uri);
        }
    }

    function handleDeleteMemberClick(memberId){
        console.log(memberId)
        setMemberList( memberList.filter(member => member.id !== memberId) )
    }

        return (
            <View style={styles.container}>
                <View style={styles.headerContainer}>
                    <Image style={styles.groupImage} source={{uri: group.image ?? "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"}}/>
                    <TouchableOpacity
                        style={styles.buttonContainer}
                        onPress={onChangeProfilePicture}
                    >
                        <Text style={styles.text} >Change</Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.contentContainer}>
                    <View style={{ flex: 2, backgroundColor: "white", marginBottom: 10 }}>
                        <GroupDetails leftText={"Group Name"} rightText={group.name}/>
                        <GroupDetails leftText={"Group Description"} rightText={group.description}/>
                        {ContentSeparator(0.7)}
                    </View>
                    <Text style={{margin: 5}}>Members: {users.length}</Text>
                    <View style={{ flex: 4, backgroundColor: "white" }}>
                        <FlatList
                            contentContainerStyle={styles.memberList}
                            data={users}
                            ItemSeparatorComponent={MemberSeparator}
                            stickyFooterIndices={[0]}
                            renderItem={({item}) =>
                                <MemberCard
                                    style={{
                                        marginHorizontal: 20,
                                        marginVertical: 2,
                                    }}
                                    key={item.id}
                                    memberId={item.id}
                                    image={item.userProfileImage}
                                    username={item.email}
                                    datejoined={item.date}
                                    handleDelete={handleDeleteMemberClick}
                                />
                            }
                        />
                    </View>
                </View>
            </View>
        );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: "column",
    },
    headerContainer:{
        flex: 4,
        backgroundColor: "white",
        alignItems: "center"

    },
    groupImage: {
        width: 100,
        height: 100,
        borderRadius: 63,
        borderWidth: 4,
        borderColor: "#7c7fca",
        marginBottom:10,
        alignSelf:"center",
        marginTop:30,
    },
    buttonContainer: {
        marginTop:10,
        height:45,
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        marginBottom:20,
        width:150,
        borderRadius:50,
        backgroundColor: "#7c7fca",
    },
    contentContainer: {
        flex: 8,
        backgroundColor: "white",
        flexDirection: "column",
    },

    text: {
        fontSize: 16,
        color: "white",
        textAlign: "center",
    },
    memberList: {
        marginBottom: 15
    }
});
