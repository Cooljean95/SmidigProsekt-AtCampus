import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import {
    FlatList, ImageBackground, Modal, Platform, StatusBar,
    StyleSheet,
    Text,
    TouchableOpacity,
    View,
    TextInput,
    KeyboardAvoidingView, KeyboardAvoidingViewComponent
} from "react-native";

import PostPreview from "../components/PostPreview";
import GroupPage from "../components/GroupPage";
import SimpleButton from "../components/SimpleButton";
import {Feather} from "@expo/vector-icons";
import {
    addPostToGroup,
    getAllPostsByGroup,
    getUsersFromGroup
} from "../services/GroupService";
import {useSelector} from "react-redux";

const postPreviews = [
    {
        id: 1,
        image: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        name: "Victoria Hansen",
        title: "Math Study Group",
        preview: "My lovely post",
        date: "4 March",
    },
    {
        id: 2,
        image: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        name: "Victoria Hansen",
        title: "Math Study Group",
        preview: "My lovely post",
        date: "4 March",
    },
    {
        id: 3,
        image: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        name: "Victoria Hansen",
        title: "Math Study Group",
        preview: "My lovely post",
        date: "4 March",
    },
    {
        id: 4,
        image: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        name: "Victoria Hansen",
        title: "Math Study Group",
        preview: "My lovely post",
        date: "4 March",
    },
    {
        id: 5,
        image: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        name: "Victoria Hansen",
        title: "Math Study Group",
        preview: "My lovely post",
        date: "4 March",
    },
    {
        id: 6,
        image: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        name: "Victoria Hansen",
        title: "Math Study Group",
        preview: "My lovely post",
        date: "4 March",
    },
    {
        id: 7,
        image: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        name: "Victoria Hansen",
        title: "Math Study Group",
        preview: "My lovely post",
        date: "4 March",
    },
    {
        id: 8,
        image: "https://images.unsplash.com/photo-1567168539593-59673ababaae?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        name: "Victoria Hansen",
        title: "Math Study Group",
        preview: "My lovely post",
        date: "4 March",
    }
];

function Separator() {
    return <View style={styles.separator}/>
}


function GroupScreen({navigation, route}) {

    const [isModalPostVisible, setIsModalPostVisible] = useState(false)
    const [postMessage, setPostMessage] = useState("")
    const [postTitle, setPostTitle] = useState("")
    const {group} = route.params
    const {userId} = useSelector(state => state.auth)
    const [posts, setPosts] = useState([])

    const [users, setUsers] = useState([])


    const postMessageRef = useRef();
    const postTitleRef = useRef();

    useEffect(() => {
        getAllPostsByGroup(group.id).then((res) => {
            setPosts(res.data)
        }).catch((err) => {
            console.log(err.toString())
        })
        getUsersFromGroup(group.id).then((res) =>{
            setUsers(res.data)
        }).catch((err) =>{
            console.log(err.toString())
        })

    },[])

    console.log(users.length)

    function handlePostMessageClick(){
        setIsModalPostVisible(false)
        addPostToGroup(group.id, postTitle, postMessage, userId)
            .then(() => {
                getAllPostsByGroup(group.id).then((res) => {
                    setPosts(res.data)
                }).catch((err) => {
                    console.log(err.toString())
                })
            })
            .catch((err) => {
                console.log("Error adding a post to group", err)
            })

        setPostMessage("")
        setPostTitle("")
    }

    function handleCancelPostMessage(){
        setIsModalPostVisible(false)
        setPostMessage("")
    }

    return (
        <View style={{ flex: 1 }}>
            <ImageBackground source={{uri: group.image}} style={styles.ImageBackground}>
                <StatusBar style="auto" />
            </ImageBackground>

            <View style={{ flex: 0.35, padding: 10, marginBottom: 5 }}>
                <Text style={styles.groupName}>
                    {group.name}
                </Text>

                <Text style={styles.description}>
                    {group.description}
                </Text>

                <Text style={styles.memberCount}>
                    {/*TODO: Add functionality to get member count in services. */}
                    Members: {users.length}
                </Text>
            </View>
            <View style={{ flex: 0.8 }}>
                <Text style={styles.subtitle}>
                    Recent Activity
                </Text>

                <FlatList
                    contentContainerStyle={styles.postPreviews}
                    data={posts}
                    ItemSeparatorComponent={Separator}
                    stickyFooterIndices={[0]}
                    renderItem={({item}) =>
                        <PostPreview
                            style={{
                                marginHorizontal: 20,
                                marginVertical: 2,
                            }}
                            key={item.id}
                            image={item.userEntity.userProfileImage}
                            title={item.title}
                            preview={item.body}
                            date={item.date}
                            onPress={() => navigation.navigate("Group Comment", {post: item})}
                        />
                    }
                />
            </View>
            <View style={ styles.bottomContainer }>
                <View stylye={styles.settingButtonContainer}>
                    <TouchableOpacity
                        style={ styles.containerButtonSettings}
                        activeOpacity={0.6}
                        onPress={ () => navigation.navigate("Group Options", {group})}
                    >
                        <Feather style={{alignSelf: "center"}} name={"settings"} color={"black"} size={22} />
                    </TouchableOpacity>
                </View>

                <View style={styles.postButtonContainer}>
                    <SimpleButton onPress={() => setIsModalPostVisible(true)} text={"Post"}></SimpleButton>
                </View>

                <View style={styles.requestButtonContainer}>
                    <TouchableOpacity
                        style={ styles.containerButtonAddUser}
                        activeOpacity={0.6}
                        onPress={ () => navigation.navigate("Group Requests", {group})}
                    >
                        <Feather style={{alignSelf: "center"}} name={"user-plus"} color={"black"} size={22} />
                    </TouchableOpacity>
                </View>
            </View>

            <Modal
                transparent={true}
                animationType="fade"
                visible={isModalPostVisible}
                nRequestClose={() => setIsModalPostVisible(false)}
                avoidKeyboard={true}
            >
                <TouchableOpacity
                    style={{flex:0.5, backgroundColor: "gray", opacity: 0.5,}}
                    onPress={handleCancelPostMessage}
                />

                <KeyboardAvoidingView
                    style={styles.backgroundModal}
                    behavior={Platform.OS === "ios" ? "padding" : null}
                >
                    <TextInput
                        ref={postTitleRef}
                        title={"Post title"}
                        style={styles.titleInput}
                        multiline={true}
                        numberOfLines={2}
                        value={postTitle}
                        onChangeText={val => setPostTitle(val)}
                        placeholder={"Title"}
                    />

                    <TextInput
                        ref={postMessageRef}
                        style={styles.input}
                        multiline={true}
                        numberOfLines={2}
                        onChangeText={setPostMessage}
                        value={postMessage}
                        placeholder={"Write a post to your group here :)"}
                    />

                    <View style={{margin: 10}}>
                        <SimpleButton style={{top: -0}} onPress={handlePostMessageClick} text={"Post"}></SimpleButton>
                    </View>

                </KeyboardAvoidingView>
            </Modal>
        </View>
    );
}

const styles = StyleSheet.create({
    background: {
        position: "absolute",
        top: 0,
        left: 0,
        bottom: 0,
        right: 0,
        flex: 1,
        backgroundColor: "#000",
    },
    containerButtonAddUser: {
        marginLeft: 33,
        borderWidth: 1,
        borderRadius: 50,
        borderColor: "#d3d3d3",
        paddingVertical: 13,
        paddingHorizontal: 15,
        backgroundColor: "#ffffff",
        width: "55%"
    },
    containerButtonSettings: {
        marginLeft: 5,
        borderWidth: 1,
        marginRight: 40,
        borderRadius: 50,
        borderColor: "#d3d3d3",
        paddingVertical: 13,
        paddingHorizontal: 15,
        backgroundColor: "#ffffff",
        width: "60%",
    },
    blur: {
        position: "absolute",
        top: 0,
        right: 0,
        bottom: 0,
        left: 0,
        flex: 1,
        zIndex: 0,
    },
    container: {
        flex: 1,
        backgroundColor: "white",
    },
    title: {
        fontSize: 24,
        fontWeight: "bold",
        //marginBottom: 10,
    },
    groupName: {
        flex: 2,
        fontSize: 25,
        textAlign: "center",
        padding: 5,
    },
    description: {
        flex: 2,
        fontSize: 16,
        textAlign: "center",
        padding: 5,
    },
    memberCount: {
        flex: 1.5,
        fontSize: 16,
        textAlign: "center",
        fontWeight: "bold",
    },
    separator: {
        height: 10,
    },
    subtitle: {
        fontSize: 20,
        fontWeight: "bold",
        marginBottom: 15,
        marginLeft: 10,
    },
    postPreviews: {
        marginBottom: 15,
    },
    bottomContainer: {
        display: "flex",
        flex: 0.15,
        flexDirection: "row",
        padding:5,
        paddingLeft: 15,
    },
    buttonContainer: {
        display: "flex",
        flex: 0.5,
    },
    ImageBackground: {
        flex: 0.4,
        resizeMode: "contain",
        width: "100%",
        alignItems: "center",
    },
    postButtonContainer: {
        flex: 1.5,
    },
    settingButtonContainer: {
        flex: 1
    },
    requestButtonContainer: {
        flex: 1,
    },
    backgroundModal: {
        backgroundColor: "#ffffff",
        flex: 1
    },
    input: {
        padding: 15,
        margin: 10,
        borderRadius: 20,
        backgroundColor: "#f1f0f0",
        maxHeight: 155,
    },
    titleInput: {
        padding: 15,
        marginTop: 20,
        margin: 10,
        borderRadius: 20,
        backgroundColor: "#f1f0f0",
        maxHeight: 60,
    },
    keyboardContainer: {
        flex: 1,
        backgroundColor: "white",
    }
});

export default GroupScreen;
