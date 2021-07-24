package com.gsi.tm.models

import com.gsi.tm.enums.TypeProfile

class Manager(fullName: String, occupation: String, globalId: String, isAccountLocal: Boolean) :
    Person(
        fullName = fullName,
        occupation = occupation,
        globalId = globalId,
        typeProfile = TypeProfile.Manager.name,
        isAccountLocal = isAccountLocal
    )
