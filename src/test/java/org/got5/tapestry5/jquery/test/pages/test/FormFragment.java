//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.got5.tapestry5.jquery.test.pages.test;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.got5.tapestry5.jquery.test.entities.ShippingAddress;

public class FormFragment
{
    @Persist
    private ShippingAddress _billingAddress;

    @Persist
    private ShippingAddress _shippingAddress;

    @Persist
    private boolean _separateShipTo;
    
    @Persist
    @Property
    private int code;
    
    @Persist
    @Property
    private boolean codeVisible;

    public ShippingAddress getBillingAddress()
    {
        return _billingAddress;
    }

    public void setBillingAddress(ShippingAddress billingAddress)
    {
        _billingAddress = billingAddress;
    }

    public ShippingAddress getShippingAddress()
    {
        return _shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress)
    {
        _shippingAddress = shippingAddress;
    }

    public boolean isSeparateShipTo()
    {
        return _separateShipTo;
    }

    public void setSeparateShipTo(boolean separateShipTo)
    {
        _separateShipTo = separateShipTo;
    }

}
