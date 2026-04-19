# syntax=docker/dockerfile:1

ARG NODE_VERSION=22.13.1

# --- Build Stage ---
FROM node:${NODE_VERSION}-slim AS builder
WORKDIR /app

COPY --link package.json package-lock.json ./
RUN --mount=type=cache,target=/root/.npm npm ci

COPY --link . .
RUN npm run build

# --- Production Stage ---
FROM nginx:1.27-alpine AS final

COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
COPY nginx/entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

ENV API_BASE_URL=""

EXPOSE 3000
ENTRYPOINT ["/entrypoint.sh"]
