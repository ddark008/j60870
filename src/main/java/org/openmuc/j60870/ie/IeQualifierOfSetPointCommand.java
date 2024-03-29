/*
 * Copyright 2014-20 Fraunhofer ISE
 *
 * This file is part of j60870.
 * For more information visit http://www.openmuc.org
 *
 * j60870 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * j60870 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with j60870.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openmuc.j60870.ie;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Represents a qualifier of set-point command (QOS) information element.
 */
public class IeQualifierOfSetPointCommand extends InformationElement implements StreamEncode {

    private final int ql;
    private final boolean select;

    public IeQualifierOfSetPointCommand(int ql, boolean select) {
        this.ql = ql;
        this.select = select;
    }

    IeQualifierOfSetPointCommand(DataInputStream is) throws IOException {
        int b1 = (is.readByte() & 0xff);
        ql = b1 & 0x7f;
        select = ((b1 & 0x80) == 0x80);
    }

    @Override
    int encode(byte[] buffer, int i) {
        buffer[i] = (byte) ql;
        if (select) {
            buffer[i] |= 0x80;
        }
        return 1;
    }

    @Override
    public void encode(ByteArrayOutputStream bOutput) {
        byte temp_val = (byte) ql;
        if (select) {
            temp_val |= 0x80;
        }
        bOutput.write(temp_val);
    }

    public int getQl() {
        return ql;
    }

    public boolean isSelect() {
        return select;
    }

    @Override
    public String toString() {
        return "Qualifier of set point command, QL: " + ql + ", select: " + select;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IeQualifierOfSetPointCommand that = (IeQualifierOfSetPointCommand) o;

        if (ql != that.ql) return false;
        return select == that.select;
    }

    @Override
    public int hashCode() {
        int result = ql;
        result = 31 * result + (select ? 1 : 0);
        return result;
    }
}
