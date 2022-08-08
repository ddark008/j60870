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

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Represents the segment of a file as transferred by ASDUs of type F_SG_NA_1 (125).
 */
public class IeFileSegment extends InformationElement {

    private final byte[] segment;
    private final int offset;
    private final int length;

    public IeFileSegment(byte[] segment, int offset, int length) {
        this.segment = segment;
        this.offset = offset;
        this.length = length;
    }

    IeFileSegment(DataInputStream is) throws IOException {

        length = (is.readByte() & 0xff);
        segment = new byte[length];

        is.readFully(segment);
        offset = 0;
    }

    @Override
    int encode(byte[] buffer, int i) {

        buffer[i++] = (byte) length;

        System.arraycopy(segment, offset, buffer, i, length);

        return length + 1;

    }

    public byte[] getSegment() {
        return segment;
    }

    @Override
    public String toString() {
        return "File segment of length: " + length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IeFileSegment that = (IeFileSegment) o;

        if (offset != that.offset) return false;
        if (length != that.length) return false;
        return Arrays.equals(segment, that.segment);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(segment);
        result = 31 * result + offset;
        result = 31 * result + length;
        return result;
    }
}
