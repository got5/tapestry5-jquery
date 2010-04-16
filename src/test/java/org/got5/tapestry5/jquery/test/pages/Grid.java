//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// 	http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.got5.tapestry5.jquery.test.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Property;

import org.got5.tapestry5.jquery.test.entities.User;

public class Grid
{
    @Property
    private User user;

    public List<User> getUsers()
    {
        return createUsers(50);
    }

    private User createUser(int i)
    {
        User u = new User();
        u.setAge(i);
        u.setFirstName("lala" + i + 10);
        u.setLastName("lolo" + i + 200);
        return u;
    }

    private List<User> createUsers(int number)
    {
        List<User> users = new ArrayList<User>();

        for (int i = 0; i < number; i++)
        {
            users.add(createUser(i));
        }

        return users;
    }
}
