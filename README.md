# 🌐 Uconnect – A Social Media App (Java Swing + MySQL)

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Swing](https://img.shields.io/badge/UI-Swing-brightgreen.svg)
![MySQL](https://img.shields.io/badge/Database-MySQL-orange.svg)
![FlatLaf](https://img.shields.io/badge/Theme-FlatLaf-black.svg)
![Status](https://img.shields.io/badge/Status-Completed-success)

---

## 📌 Project Overview

**Uconnect** is a Java-based social media desktop application built with **Swing UI**, **JDBC**, and **MySQL**. It allows users to:

- Register, login, and manage their profiles
- Post photos and blogs
- Add friends, send requests, and accept/reject them
- Chat with friends (with typing indicators)
- View timeline feeds of friends' posts
- Upload profile pictures
- Experience a dark-themed modern UI (FlatLaf)

---

## 📸 UI Highlights

- 🟣 Dark theme using [FlatLaf](https://www.formdev.com/flatlaf/)
- 🧑‍🤝‍🧑 Timeline feed layout inspired by Instagram
- 🖼️ Full image preview for uploads
- 💬 Real-time messaging interface
- 🔁 Notification and unread count for messages

---

## 🧠 Core Features

| Feature                       | Description                                                                                    |
|------------------------------|------------------------------------------------------------------------------------------------|
| ✅ Login/Register             | Email & password validation with database checks(For the login fill the username and password) |
| ✅ Profile Page               | View and update user details, profile picture                                                  |
| ✅ Timeline Feed              | Display posts and blogs by friends or public users                                             |
| ✅ Chat System                | Send/receive messages with friends                                                             |
| ✅ Friends System             | Send, receive, accept/reject friend requests                                                   |
| ✅ Likes & Comments (Basic)   | Like button for posts                                                                          |
| ✅ Responsive UI              | Built with Swing using layout managers and good alignment                                      |

---

## 🗃️ Technologies Used

- **Java 17**
- **Swing**
- **MySQL** (with JDBC connector)
- **FlatLaf** (dark theme)
- **Maven / IntelliJ IDEA** (recommended IDE)

---

## ⚙️ Database Schema Summary

| Table        | Purpose                        |
|--------------|--------------------------------|
| `users`      | Stores user data               |
| `posts`      | Stores photo uploads           |
| `blogs`      | Stores text blogs              |
| `messages`   | Stores chat messages           |
| `likes`      | Tracks post likes              |
| `friendships`| Manages friend relationships   |

---

## 🚀 How to Run the Project

### 1. ✅ Prerequisites

- Java JDK 17+
- MySQL Server
- IntelliJ IDEA (or any Java IDE)
- FlatLaf JAR added in `lib/`
- JDBC MySQL Connector JAR in `lib/`

### 2. 📦 Project Setup

```bash
git clone https://github.com/Sumit-0626/Uconnect.git
cd Uconnect
