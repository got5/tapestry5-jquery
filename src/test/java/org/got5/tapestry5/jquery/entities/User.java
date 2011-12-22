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

package org.got5.tapestry5.jquery.entities;

import java.util.List;
import java.util.UUID;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.tree.DefaultTreeModel;
import org.apache.tapestry5.tree.TreeModel;
import org.apache.tapestry5.tree.TreeModelAdapter;

public class User
{
    @NonVisual
    private long id;

    private String firstName;

    private String lastName;

    private int age;

    public final String uuid = UUID.randomUUID().toString();
    
    public final List<User> children = CollectionFactory.newList();
    
    public User(String lastName, String firstName, int age) {
    	super();
    	this.lastName = lastName;
    	this.firstName = firstName;
    	this.age = age;
    }
    
	public User(String nom) {
		this(nom, null, 0);
	}
	public User() {
		this(null);
	}
	
    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }
    
	
    
    
    public static final User ROOT = new User("<root>");
	
	public User addChildrenNamed(String... names){
		
		for(String name : names){
			children.add(new User(name));
		}
		return this;
	}
	
	public User addChild(User user){
		
		children.add(user);
		
		return this;
	}
	
	public User seek(String uuid){
		
		if(this.uuid.equals(uuid)) return this;
		
		for(User child : children){
			User match = child.seek(uuid);
			
			if(match != null) return match;
		}
		
		return null;
	}
	
	static {
		
		ROOT.addChild(new User("Renault")
						.addChild(new User("Mégane"))
						.addChild(new User("Clio")
							.addChildrenNamed("Clio Campus", "Clio Sport")
						)
					 )
			.addChild(new User("Ferarri").addChildrenNamed("F430", "California"));
		
	}
	
	public static TreeModel<User> createTreeModel(){
		
		ValueEncoder encoder = new ValueEncoder<User>() {

			public String toClient(User arg0) {
				return arg0.uuid;
			}

			public User toValue(String arg0) {
				return User.ROOT.seek(arg0);
			}
		};
		
		TreeModelAdapter<User> adapter = new TreeModelAdapter<User>() {

			public List<User> getChildren(User arg0) {
				return arg0.children;
			}

			public String getLabel(User arg0) {
				return arg0.getLastName();
			}

			public boolean hasChildren(User arg0) {
				return !arg0.children.isEmpty();
			}

			public boolean isLeaf(User arg0) {
				return arg0.children.isEmpty();
			}
			
		};
		
		return new DefaultTreeModel<User>(encoder, adapter, User.ROOT.children);
			
	}
}