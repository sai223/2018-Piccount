class User {
    constructor(id, password, name, birthday, phoneNumber, backLink) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.backLink = backLink;
    }
}

module.exports = User;
