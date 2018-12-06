package com.sanshengshui.server.common.data.id;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-6 下午3:50
 */
public abstract class UUIDBased implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID id;

    public UUIDBased() {
        this(UUID.randomUUID());
    }

    public UUIDBased(UUID id) {
        super();
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UUIDBased other = (UUIDBased) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
