# syntax=docker/dockerfile:1

ARG NODE_VERSION=22.13.1

# --- Build Stage ---
FROM node:${NODE_VERSION}-slim AS builder
WORKDIR /app

COPY --link package.json package-lock.json ./
RUN --mount=type=cache,target=/root/.npm npm ci

COPY --link . .
RUN npm run build

RUN rm -rf node_modules && npm ci --omit=dev

# --- Production Stage ---
FROM node:${NODE_VERSION}-slim AS final
WORKDIR /app

RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup

COPY --from=builder /app/build ./build
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/package*.json ./
COPY --from=builder /app/public ./public

ENV NODE_ENV=production
ENV NODE_OPTIONS="--max-old-space-size=4096"

# Fix EACCES (/nonexistent) error
ENV HOME=/home/appuser
RUN mkdir -p /home/appuser && chown -R appuser:appgroup /home/appuser

USER appuser

EXPOSE 3000
CMD ["npx", "serve", "-s", "build", "-l", "3000"]
