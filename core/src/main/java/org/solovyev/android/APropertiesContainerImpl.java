package org.solovyev.android;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JObject;
import org.solovyev.common.clone.CloneUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: serso
 * Date: 8/21/12
 * Time: 2:18 PM
 */
public class APropertiesContainerImpl extends JObject implements APropertiesContainer {

    @NotNull
    private Map<String, AProperty> properties = new HashMap<String, AProperty>();

    public APropertiesContainerImpl() {
    }

    @NotNull
    public static APropertiesContainer copyOf(@NotNull APropertiesContainer propertiesContainer) {
        return propertiesContainer.clone();
    }

    @NotNull
    public static APropertiesContainer newInstance(@NotNull Collection<AProperty> properties) {
        final APropertiesContainerImpl result = new APropertiesContainerImpl();

        for (AProperty property : properties) {
            result.setProperty(property);
        }

        return result;
    }

    @NotNull
    public static APropertiesContainer newInstance(@NotNull Map<String, AProperty> properties) {
        final APropertiesContainerImpl result = new APropertiesContainerImpl();

        for (AProperty property : properties.values()) {
            result.setProperty(property);
        }

        return result;
    }

    @NotNull
    @Override
    public APropertiesContainerImpl clone() {
        final APropertiesContainerImpl clone = (APropertiesContainerImpl) super.clone();

        clone.properties = CloneUtils.cloneMap(this.properties);

        return clone;
    }

    @Override
    @NotNull
    public AProperty setProperty(@NotNull String name, @NotNull String value) {
        final AProperty property = APropertyImpl.newInstance(name, value);

        properties.put(name, property);

        return property;
    }

    @Override
    public void setProperty(@NotNull AProperty property) {
        properties.put(property.getName(), property);
    }

    @Override
    public void setPropertiesFrom(@NotNull APropertiesContainer that) {
        for (AProperty property : that.getProperties().values()) {
              setProperty(property);
        }
    }

    @Override
    public void setPropertiesFrom(@NotNull Collection<AProperty> properties) {
        for (AProperty property : properties) {
            setProperty(property);
        }
    }

    @Override
    @Nullable
    public AProperty removeProperty(@NotNull String name) {
        return properties.remove(name);
    }

    @Override
    @Nullable
    public AProperty getProperty(@NotNull String name) {
        return properties.get(name);
    }

    @Override
    public String getPropertyValue(@NotNull String name) {
        final AProperty property = properties.get(name);
        return property == null ? null : property.getValue();
    }

    @Override
    public void clearProperties() {
        properties.clear();
    }

    @Override
    @NotNull
    public Map<String, AProperty> getProperties() {
        return Collections.unmodifiableMap(this.properties);
    }

    @Override
    @NotNull
    public Collection<AProperty> getPropertiesCollection() {
       return Collections.unmodifiableCollection(this.properties.values());
    }
}
